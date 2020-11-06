using AutoMapper;
using DrinkUp.Common;
using DrinkUp.Common.Filter;
using DrinkUp.DAL.Entities;
using DrinkUp.Models;
using DrinkUp.Models.Common;
using DrinkUp.Repository;
using DrinkUp.Service.Common;
using Microsoft.EntityFrameworkCore.ChangeTracking;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.IO;
using System.Linq;
using System.Net.Http.Headers;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service
{
    public class KorisnikService : IKorisnikService
    {
        protected GenericRepository<Korisnik> Repository { get; private set; }
        protected GenericRepository<KorisnikAktivacija> AktivacijaRepository { get; private set; }
        protected GenericRepository<KorisnikReset> ResetRepository { get; private set; }
        protected GenericRepository<Kod> KodRepository { get; private set; }
        protected IMapper Mapper { get; private set; }
        private readonly UnitOfWork unitOfWork;

        public KorisnikService(IMapper mapper)
        {
            unitOfWork = new UnitOfWork();
            Repository = unitOfWork.KorisnikRepository;
            AktivacijaRepository = unitOfWork.KorisnikAktivacijaRepository;
            KodRepository = unitOfWork.KodRepository;
            ResetRepository = unitOfWork.KorisnikResetRepository;
            Mapper = mapper;
        }

        public async Task DeleteAsync(int id)
        {
            await Repository.DeleteAsync(id);
            await unitOfWork.SaveAsync();
        }

        public async Task<IEnumerable<IKorisnikModel>> GetAsync(GetParams<IKorisnikModel> getParams)
        {
            return Mapper.Map<ICollection<IKorisnikModel>>(await Repository.Get(Mapper.Map<GetParams<Korisnik>>(getParams)));
        }

        public async Task<IKorisnikModel> GetAsync(int id)
        {
            return Mapper.Map<IKorisnikModel>(await Repository.GetByID(id));
        }

        public async Task<string> InsertAsync(IKorisnikModel entity)
        {
            entity.Lozinka = Sha256(entity.Lozinka);
            entity.Aktivan = false;
            EntityEntry<Korisnik> entry = Repository.Insert(Mapper.Map<Korisnik>(entity));

            string token = GenerateNewToken();
            Kod kod = new Kod()
            {
                Id = token,
                DatumKreiranja = DateTime.Now
            };
            KodRepository.Insert(kod);
            await unitOfWork.SaveAsync();

            KorisnikAktivacija aktivacija = new KorisnikAktivacija()
            {
                KodId = token,
                KorisnikId = entry.Entity.Id
            };

            AktivacijaRepository.Insert(aktivacija);
            await unitOfWork.SaveAsync();

            return token;
        }

        public async Task ActivateAccountAsync(string token, GetParams<IKorisnikModel> getParams)
        {
            FilterParams tokenParam = new FilterParams()
            {
                ColumnName = "KodId",
                FilterOption = FilterOptions.IsEqualTo,
                FilterValue = token
            };
            getParams.FilterParam = new[] { tokenParam };
            getParams.Include = "Kod, Korisnik";

            IEnumerable<KorisnikAktivacija> aktivacije = (await AktivacijaRepository.Get(Mapper.Map<GetParams<KorisnikAktivacija>>(getParams)))
                .Where(x => x.Kod.DatumKreiranja.AddDays(3) > DateTime.Now);

            if (aktivacije != null && aktivacije.Count() > 0)
            {
                KorisnikAktivacija aktivacija = aktivacije.First();
                Korisnik korisnik = aktivacija.Korisnik;
                korisnik.Aktivan = Convert.ToByte(true);
                string kodId = aktivacija.KodId;

                Repository.Update(korisnik);
                AktivacijaRepository.Delete(aktivacija);
                await KodRepository.DeleteAsync(kodId);
                await unitOfWork.SaveAsync();
            }
            else
            {
                throw new InvalidOperationException();
            }
        }

        public async Task<string> ResetPasswordToken(IKorisnikModel korisnik)
        {
            if (korisnik == null)
            {
                throw new InvalidOperationException();
            }
             
            string token = GenerateNewToken();
            Kod kod = new Kod()
            {
                Id = token,
                DatumKreiranja = DateTime.Now
            };
            KodRepository.Insert(kod);
            await unitOfWork.SaveAsync();

            KorisnikReset reset = new KorisnikReset()
            {
                KodId = token,
                KorisnikId = korisnik.Id
            };

            ResetRepository.Insert(reset);
            await unitOfWork.SaveAsync();

            return token;
        }

        public async Task<IKorisnikReset> ValidatePasswordReset(string token, string email, GetParams<IKorisnikModel> getParams)
        {
            FilterParams tokenParam = new FilterParams()
            {
                ColumnName = "KodId",
                FilterOption = FilterOptions.IsEqualTo,
                FilterValue = token
            };
            getParams.FilterParam = new[] { tokenParam };
            getParams.Include = "Korisnik, Kod";
            KorisnikReset reset = (await ResetRepository.Get(Mapper.Map<GetParams<KorisnikReset>>(getParams)))
                .Where(x => x.Kod.DatumKreiranja.AddDays(3) > DateTime.Now).FirstOrDefault();

            return Mapper.Map<IKorisnikReset>(reset);
        }

        public async Task UpdateAsync(IKorisnikModel entity)
        {
            Repository.Update(Mapper.Map<Korisnik>(entity));
            await unitOfWork.SaveAsync();
        }

        private string Sha256(string rawData)
        {
            using (SHA256 sha256Hash = SHA256.Create())
            {
                string salt = @"}#f4ga~g%7hjg4&j(7mk?/!bjčj56zydrEQWE|€°˘˘°5J#E6WT;IO[JN";
                byte[] bytes = sha256Hash.ComputeHash(Encoding.UTF8.GetBytes(salt + rawData));
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < bytes.Length; i++)
                {
                    builder.Append(bytes[i].ToString("x2"));
                }

                return builder.ToString();
            }
        }

        private string GenerateNewToken()
        {
            return Convert.ToBase64String(Guid.NewGuid().ToByteArray());
        }

        public string GetAccountActivationPage(string message)
        {
            string FilePath = Directory.GetCurrentDirectory() + "\\wwwroot\\Templates\\AccountActivationSuccess.html";
            StreamReader str = new StreamReader(FilePath);
            string mailText = str.ReadToEnd();
            str.Close();
            mailText = mailText.Replace("[message]", message);
            return mailText;
        }

        public string GetPasswordResetForm(string token, string email)
        {
            string FilePath = Directory.GetCurrentDirectory() + "\\wwwroot\\Templates\\PasswordResetForm.html";
            StreamReader str = new StreamReader(FilePath);
            string mailText = str.ReadToEnd();
            str.Close();
            mailText = mailText.Replace("[token]", token).Replace("[email]", email);
            return mailText;
        }

        public async Task ResetPassword(IKorisnikReset korisnikReset, string password)
        {
            if (korisnikReset != null)
            {
                string kodId = korisnikReset.KodId;
                Korisnik korisnik = await Repository.GetByID(korisnikReset.KorisnikId);
                korisnik.Lozinka = Sha256(password);

                Repository.Update(korisnik);
                await ResetRepository.DeleteAsync(korisnikReset.Id);
                await KodRepository.DeleteAsync(kodId);
                await unitOfWork.SaveAsync();
            }
            else
            {
                throw new InvalidOperationException();
            }
        }

        public async Task<IKorisnikModel> Login(string email, string password, GetParams<IKorisnikModel> getParams)
        {
            FilterParams emailParam = new FilterParams()
            {
                ColumnName = "Email",
                FilterOption = FilterOptions.IsEqualTo,
                FilterValue = email
            };
            password = Sha256(password);
            FilterParams passwordParam = new FilterParams()
            {
                ColumnName = "Lozinka",
                FilterOption = FilterOptions.IsEqualTo,
                FilterValue = password
            };
            getParams.FilterParam = new[] { emailParam, passwordParam };

            return Mapper.Map<IKorisnikModel>((await Repository.Get(Mapper.Map<GetParams<Korisnik>>(getParams))).First());
        }
    }
}
