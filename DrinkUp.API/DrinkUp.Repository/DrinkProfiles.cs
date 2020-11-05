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
            CreateMap<KorisnikModel, Korisnik>().PreserveReferences().ReverseMap();
            CreateMap<IKorisnikModel, Korisnik>().PreserveReferences().ReverseMap();
            CreateMap<KorisnikTokenModel, KorisnikToken>().PreserveReferences().ReverseMap();
            CreateMap<IKorisnikTokenModel, KorisnikToken>().PreserveReferences().ReverseMap();
            CreateMap<ObjektModel, Objekt>().PreserveReferences().ReverseMap();
            CreateMap<IObjektModel, Objekt>().PreserveReferences().ReverseMap();
            CreateMap<ObjektPonudaModel, ObjektPonuda>().PreserveReferences().ReverseMap();
            CreateMap<IObjektPonudaModel, ObjektPonuda>().PreserveReferences().ReverseMap();
            CreateMap<PonudaModel, Ponuda>().PreserveReferences().ReverseMap();
            CreateMap<IPonudaModel, Ponuda>().PreserveReferences().ReverseMap();
            CreateMap<TokenModel, Token>().PreserveReferences().ReverseMap();
            CreateMap<ITokenModel, Token>().PreserveReferences().ReverseMap();
            CreateMap<UlogaModel, Uloga>().PreserveReferences().ReverseMap();
            CreateMap<IUlogaModel, Uloga>().PreserveReferences().ReverseMap();
            CreateMap<VrstaPonudeModel, VrstaPonude>().PreserveReferences().ReverseMap();
            CreateMap<IVrstaPonudeModel, VrstaPonude>().PreserveReferences().ReverseMap();
            CreateMap<ZaposlenikObjektModel, ZaposlenikObjekt>().PreserveReferences().ReverseMap();
            CreateMap<IZaposlenikObjektModel, ZaposlenikObjekt>().PreserveReferences().ReverseMap();
        }
    }
}
