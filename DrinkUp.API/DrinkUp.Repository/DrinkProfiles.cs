using AutoMapper;
using DrinkUp.DAL.Entities;
using DrinkUp.Models;
using DrinkUp.Models.Common;
using System;

namespace DrinkUp.Repository
{
    public class DrinkProfiles : Profile
    {
        public DrinkProfiles()
        {
            CreateMap<KorisnikModel, KorisnikEntity>().PreserveReferences().ReverseMap();
            CreateMap<IKorisnikModel, KorisnikEntity>().PreserveReferences().ReverseMap();
            CreateMap<KorisnikTokenModel, KorisnikTokenEntity>().PreserveReferences().ReverseMap();
            CreateMap<IKorisnikTokenModel, KorisnikTokenEntity>().PreserveReferences().ReverseMap();
            CreateMap<ObjektModel, ObjektEntity>().PreserveReferences().ReverseMap();
            CreateMap<IObjektModel, ObjektEntity>().PreserveReferences().ReverseMap();
            CreateMap<ObjektPonudaModel, ObjektPonudaEntity>().PreserveReferences().ReverseMap();
            CreateMap<IObjektPonudaModel, ObjektPonudaEntity>().PreserveReferences().ReverseMap();
            CreateMap<PonudaModel, PonudaEntity>().PreserveReferences().ReverseMap();
            CreateMap<IPonudaModel, PonudaEntity>().PreserveReferences().ReverseMap();
            CreateMap<TokenModel, TokenEntity>().PreserveReferences().ReverseMap();
            CreateMap<ITokenModel, TokenEntity>().PreserveReferences().ReverseMap();
            CreateMap<UlogaModel, UlogaEntity>().PreserveReferences().ReverseMap();
            CreateMap<IUlogaModel, UlogaEntity>().PreserveReferences().ReverseMap();
            CreateMap<VrstaPonudeModel, VrstaPonudeEntity>().PreserveReferences().ReverseMap();
            CreateMap<IVrstaPonudeModel, VrstaPonudeEntity>().PreserveReferences().ReverseMap();
            CreateMap<ZaposlenikObjektModel, ZaposlenikObjektEntity>().PreserveReferences().ReverseMap();
            CreateMap<IZaposlenikObjektModel, ZaposlenikObjektEntity>().PreserveReferences().ReverseMap();
        }
    }
}
