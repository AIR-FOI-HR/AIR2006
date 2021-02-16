using AutoMapper;
using DrinkUp.Common;
using DrinkUp.Common.Filter;
using DrinkUp.DAL.Entities;
using DrinkUp.Models.Common;
using DrinkUp.Repository;
using DrinkUp.Service.Common;
using QRCoder;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service
{
    public class TokenService : ITokenService
    {
        protected GenericRepository<Token> Repository { get; private set; }
        protected GenericRepository<Ponuda> PonudaRepository { get; private set; }
        protected IMapper Mapper { get; private set; }
        public IPonudaService PonudaService { get; }

        private readonly UnitOfWork unitOfWork;

        public TokenService(IMapper mapper, IPonudaService ponudaService)
        {
            unitOfWork = new UnitOfWork();
            Repository = unitOfWork.TokenRepository;
            PonudaRepository = unitOfWork.PonudaRepository;
            Mapper = mapper;
            PonudaService = ponudaService;
        }

        public async Task DeleteAsync(string id)
        {
            await Repository.DeleteAsync(id);
            await unitOfWork.SaveAsync();
        }

        public async Task<IEnumerable<ITokenModel>> GetAsync(GetParams<ITokenModel> getParams)
        {
            return Mapper.Map<ICollection<ITokenModel>>(await Repository.Get(Mapper.Map<GetParams<Token>>(getParams)));
        }

        public async Task<ITokenModel> GetAsync(string id)
        {
            return Mapper.Map<ITokenModel>(await Repository.GetByID(id));
        }

        public async Task<byte[]> InsertAsync(ITokenModel entity, GetParams<ITokenModel> getParams)
        {
            Ponuda ponudaModel = (await PonudaRepository.Get(x => x.Id == entity.PonudaId)).FirstOrDefault();

            if (ponudaModel == null)
            {
                throw new InvalidOperationException();
            }

            FilterParams ponudaFilterParam = new FilterParams()
            {
                ColumnName = "ponudaId",
                FilterOption = FilterOptions.IsEqualTo,
                FilterValue = entity.PonudaId.ToString()
            };
            FilterParams iskoristenFilterParam = new FilterParams()
            {
                ColumnName = "iskoristen",
                FilterOption = FilterOptions.IsEqualTo,
                FilterValue = "1"
            };
            getParams.FilterParam = new[] { ponudaFilterParam, iskoristenFilterParam };
            int usedTokens = (await GetAsync(getParams)).Count();

            if (usedTokens >= ponudaModel.BrojTokena)
            {
                throw new InvalidOperationException();
            }

            string id;
            using (SHA1 hash = SHA1.Create())
            {
                Guid guid = Guid.NewGuid();
                byte[] bytes = hash.ComputeHash(guid.ToByteArray());

                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < bytes.Length; i++)
                {
                    builder.Append(bytes[i].ToString("x2"));
                }
                id = builder.ToString();
            }

            QRCodeGenerator generator = new QRCodeGenerator();
            QRCodeData codeData = generator.CreateQrCode("https://air2006.azurewebsites.net/api/token/activate/" + id, QRCodeGenerator.ECCLevel.Q);
            QRCode code = new QRCode(codeData);
            Bitmap image = code.GetGraphic(20);

            byte[] imageBytes;
            using (MemoryStream memoryStream = new MemoryStream())
            {
                image.Save(memoryStream, ImageFormat.Png);
                imageBytes = memoryStream.ToArray();
            }

            entity.Id = id;
            entity.QR = imageBytes;

            Repository.Insert(Mapper.Map<Token>(entity));
            await unitOfWork.SaveAsync();
            return imageBytes;
        }

        public async Task UpdateAsync(ITokenModel entity)
        {
            Repository.Update(Mapper.Map<Token>(entity));
            await unitOfWork.SaveAsync();
        }

        public async Task ActivateAsync(string id, GetParams<ITokenModel> getParams)
        {
            Token tokenModel = (await Repository.Get(x => x.Id == id, "Ponuda")).FirstOrDefault();
            if (tokenModel == null)
            {
                throw new InvalidOperationException();
            }
            Ponuda ponudaModel = tokenModel.Ponuda;

            if (ponudaModel.BrojTokena <= 0 || tokenModel.Iskoristen == 1)
            {
                throw new InvalidOperationException();
            }

            tokenModel.Iskoristen = 1;
            tokenModel.Ponuda = null;
            Repository.Update(tokenModel);
            await unitOfWork.SaveAsync();

            ponudaModel.BrojTokena -= 1;
            await PonudaService.UpdateAsync(Mapper.Map<IPonudaModel>(ponudaModel));

        }
    }
}
