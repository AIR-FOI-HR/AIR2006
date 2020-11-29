using AutoMapper;
using DrinkUp.Models;
using DrinkUp.Models.Common;
using DrinkUp.WebAPI.REST;
using DrinkUp.WebAPI.ViewModels;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Threading.Tasks;

namespace DrinkUp.WebAPI.Profiles
{
    public class RESTProfile : Profile
    {
        public RESTProfile()
        {
            CreateMap<KorisnikModel, KorisnikREST>().PreserveReferences().ReverseMap();
            CreateMap<IKorisnikModel, KorisnikREST>().PreserveReferences().ReverseMap();
            CreateMap<KorisnikTokenModel, KorisnikTokenREST>().PreserveReferences().ReverseMap();
            CreateMap<ObjektModel, ObjektREST>().PreserveReferences().ReverseMap();
            CreateMap<PonudaModel, PonudaREST>().PreserveReferences().ReverseMap();
            CreateMap<TokenModel, TokenREST>().PreserveReferences().ReverseMap();
            CreateMap<UlogaModel, UlogaREST>().PreserveReferences().ReverseMap();
            CreateMap<ZaposlenikObjektModel, ZaposlenikObjektREST>().PreserveReferences().ReverseMap();
            CreateMap<VrstaPonudeModel, VrstaPonudeREST>().PreserveReferences().ReverseMap();

            CreateMap<KorisnikModel, KorisnikVM>().PreserveReferences().ReverseMap();
            CreateMap<KorisnikTokenModel, KorisnikTokenVM>().PreserveReferences().ReverseMap();
            CreateMap<ObjektModel, ObjektVM>().PreserveReferences().ReverseMap();
            CreateMap<PonudaModel, PonudaVM>().PreserveReferences().ReverseMap();
            CreateMap<TokenModel, TokenVM>().PreserveReferences().ReverseMap();
            CreateMap<UlogaModel, UlogaVM>().PreserveReferences().ReverseMap();
            CreateMap<VrstaPonudeModel, VrstaPonudeVM>().PreserveReferences().ReverseMap();
            CreateMap<ZaposlenikObjektModel, ZaposlenikObjektVM>().PreserveReferences().ReverseMap();

            CreateMap<Spol, string>().ConvertUsing(src => src.ToString());
        }
    }
}
