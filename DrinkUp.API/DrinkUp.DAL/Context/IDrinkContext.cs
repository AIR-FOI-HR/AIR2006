using DrinkUp.DAL.Entities;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.DAL.Context
{
    public interface IDrinkContext
    {
        DbSet<KorisnikEntity> Korisnik { get; set; }
        DbSet<KorisnikTokenEntity> KorisnikToken { get; set; }
        DbSet<ObjektEntity> Objekt { get; set; }
        DbSet<ObjektPonudaEntity> ObjektPonuda { get; set; }
        DbSet<PonudaEntity> Ponuda { get; set; }
        DbSet<TokenEntity> Token { get; set; }
        DbSet<UlogaEntity> Uloga { get; set; }
        DbSet<VrstaPonudeEntity> VrstaPonude { get; set; }
        DbSet<ZaposlenikObjektEntity> ZaposlenikObjekt { get; set; }

        Task<int> SaveChangesAsync();
        ValueTask DisposeAsync();
    }
}
