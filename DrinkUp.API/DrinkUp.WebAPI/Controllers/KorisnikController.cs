using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Formatting;
using System.Runtime.CompilerServices;
using System.Threading.Tasks;
using AutoMapper;
using DrinkUp.Common;
using DrinkUp.Common.Filter;
using DrinkUp.Common.Page;
using DrinkUp.Common.Sort;
using DrinkUp.Models;
using DrinkUp.Models.Common;
using DrinkUp.Service.Common;
using DrinkUp.WebAPI.REST;
using DrinkUp.WebAPI.ViewModels;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace DrinkUp.WebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class KorisnikController : ControllerBase
    {
        public IKorisnikService Service { get; }
        public IMailService MailService { get; }
        public IMapper Mapper { get; }
        public IFilter Filter { get; }
        public ISort Sort { get; }
        public IPagedResult PagedResult { get; }

        public KorisnikController(IKorisnikService service, IMailService mailService, IMapper mapper, IFilter filter, ISort sort, IPagedResult pagedResult)
        {
            Service = service;
            MailService = mailService;
            Mapper = mapper;
            Filter = filter;
            Sort = sort;
            PagedResult = pagedResult;
        }

        [HttpGet("")]
        public async Task<ICollection<KorisnikREST>> GetAsync(string filterColumn = "",
            string filterValue = "", int filterOption = 3, string sortBy = "",
            int sortOrder = 1, int pageSize = 10, int page = 1)
        {
            GetParams<KorisnikModel> getParams = new GetParams<KorisnikModel>()
            {
                PageNumber = page,
                PageSize = pageSize,
                Filter = Filter,
                Sort = Sort,
                Page = PagedResult
            };
            FilterParams filterParams = new FilterParams()
            {
                ColumnName = filterColumn,
                FilterValue = filterValue,
                FilterOption = (FilterOptions)filterOption
            };
            SortingParams sortingParams = new SortingParams()
            {
                ColumnName = sortBy,
                SortOrder = (SortOrders)sortOrder
            };

            getParams.FilterParam = new[] { filterParams };
            getParams.SortingParam = new[] { sortingParams };

            return Mapper.Map<List<KorisnikREST>>(await Service.GetAsync(Mapper.Map<GetParams<IKorisnikModel>>(getParams)));
        }

        [HttpGet("activate")]
        public async Task<ContentResult> Activate(string token)
        {
            try
            {
                GetParams<KorisnikModel> getParams = new GetParams<KorisnikModel>()
                {
                    PageSize = 100,
                    PageNumber = 1,
                    Filter = Filter,
                    Sort = Sort,
                    Page = PagedResult
                };
                
                await Service.ActivateAccountAsync(token, Mapper.Map<GetParams<IKorisnikModel>>(getParams));
            }
            catch
            {
                return base.Content(Service.GetAccountActivationPage("Token ne postoji ili je istekao."), "text/html");
            }
            return base.Content(Service.GetAccountActivationPage("Račun uspješno aktiviran."), "text/html");
        }

        [HttpGet("{id}")]
        public async Task<KorisnikREST> GetAsync(int id)
        {
            return Mapper.Map<KorisnikREST>(await Service.GetAsync(id));
        }

        [HttpPost("register")]
        public async Task<HttpResponseMessage> RegisterAsync(KorisnikVM korisnik)
        {
            try
            {
                string token = await Service.InsertAsync(Mapper.Map<KorisnikModel>(korisnik));
                await MailService.SendEmailAsync(MailService.CreateRegistrationMail(korisnik.Email, korisnik.Ime, token));
            }
            catch (Exception e)
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            return new HttpResponseMessage(HttpStatusCode.Created);
        }

        [HttpPut("update")]
        public async Task<HttpResponseMessage> UpdateAsync(KorisnikVM korisnik)
        {
            try
            {
                await Service.UpdateAsync(Mapper.Map<KorisnikModel>(korisnik));
            }
            catch
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);

            }
            return new HttpResponseMessage(HttpStatusCode.NoContent);
        }

        [HttpDelete("delete/{id}")]
        public async Task<HttpResponseMessage> DeleteAsync(int id)
        {
            try
            {
                await Service.DeleteAsync(id);
            }
            catch
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            return new HttpResponseMessage(HttpStatusCode.NoContent);
        }

        [HttpPost("login")]
        public async Task<KorisnikREST> Login(LoginVM user)
        {
            try
            {
                GetParams<IKorisnikModel> getParams = new GetParams<IKorisnikModel>()
                {
                    PageSize = 1,
                    PageNumber = 1,
                    Filter = Filter,
                    Sort = Sort,
                    Page = PagedResult
                };
                return Mapper.Map<KorisnikREST>(await Service.Login(user.Email, user.Lozinka, getParams));
            }
            catch
            {
                return null;
            }
        }

        [HttpPost("reset-password-mail")]
        public async Task<HttpResponseMessage> ResetPassword(ResetPasswordPostVM resetPasswordVM)
        {
            try
            {
                FilterParams emailParam = new FilterParams()
                {
                    ColumnName = "Email",
                    FilterOption = FilterOptions.IsEqualTo,
                    FilterValue = resetPasswordVM.Email
                };
                GetParams<IKorisnikModel> getParams = new GetParams<IKorisnikModel>()
                {
                    PageSize = 1,
                    PageNumber = 1,
                    Filter = Filter,
                    Sort = Sort,
                    Page = PagedResult,
                    FilterParam = new [] { emailParam }
                };
                IKorisnikModel korisnik = Mapper.Map<IKorisnikModel>((await Service.GetAsync(getParams)).FirstOrDefault());
                await MailService.SendEmailAsync(MailService.CreatePasswordResetEmail(korisnik.Email, korisnik.Ime, await Service.ResetPasswordToken(korisnik)));
            }
            catch
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
            return new HttpResponseMessage(HttpStatusCode.NoContent);
        }

        [HttpGet("reset-password")]
        public async Task<ContentResult> ResetPassword(string token, string email)
        {
            try
            {
                GetParams<KorisnikModel> getParams = new GetParams<KorisnikModel>()
                {
                    PageSize = 100,
                    PageNumber = 1,
                    Filter = Filter,
                    Sort = Sort,
                    Page = PagedResult
                };

                if (await Service.ValidatePasswordReset(token, email, Mapper.Map<GetParams<IKorisnikModel>>(getParams)) == null)
                {
                    return base.Content(Service.GetAccountActivationPage("Token ne postoji, istekao je ili je email pogrešan."), "text/html");
                }
            }
            catch
            {
                return base.Content(Service.GetAccountActivationPage("Token ne postoji, istekao je ili je email pogrešan."), "text/html");
            }
            return base.Content(Service.GetPasswordResetForm(token, email), "text/html");
        }

        [HttpPost("reset-password")]
        public async Task<ContentResult> ResetPassword([FromForm]ResetPasswordPutVM resetPasswordVM)
        {
            try
            {
                if (resetPasswordVM.Password != resetPasswordVM.Password2 || resetPasswordVM.Password == null)
                {
                    return base.Content(Service.GetAccountActivationPage("Lozinke se ne podudaraju."), "text/html");
                }
                if (resetPasswordVM.Password.Length < 6)
                {
                    return base.Content(Service.GetAccountActivationPage("Lozinka mora imati najmanje 6 znakova."), "text/html");
                }

                GetParams<KorisnikModel> getParams = new GetParams<KorisnikModel>()
                {
                    PageSize = 100,
                    PageNumber = 1,
                    Filter = Filter,
                    Sort = Sort,
                    Page = PagedResult
                };

                IKorisnikReset korisnikReset = await Service.ValidatePasswordReset(resetPasswordVM.Token, resetPasswordVM.Email, Mapper.Map<GetParams<IKorisnikModel>>(getParams));
                if (korisnikReset == null)
                {
                    return base.Content(Service.GetAccountActivationPage("Token ne postoji, istekao je ili je email pogrešan."), "text/html");
                }

                await Service.ResetPassword(korisnikReset, resetPasswordVM.Password);
                return base.Content(Service.GetAccountActivationPage("Lozinka je uspješno ažurirana."), "text/html");
            }
            catch
            {
                return base.Content(Service.GetAccountActivationPage("Token ne postoji, istekao je ili je email pogrešan."), "text/html");
            }
        }
    }
}
