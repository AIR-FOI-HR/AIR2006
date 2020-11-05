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
        DbSet<Korisnik> Korisnik { get; set; }
        DbSet<KorisnikToken> KorisnikToken { get; set; }
        DbSet<Objekt> Objekt { get; set; }
        DbSet<ObjektPonuda> ObjektPonuda { get; set; }
        DbSet<Ponuda> Ponuda { get; set; }
        DbSet<Token> Token { get; set; }
        DbSet<Uloga> Uloga { get; set; }
        DbSet<VrstaPonude> VrstaPonude { get; set; }
        DbSet<ZaposlenikObjekt> ZaposlenikObjekt { get; set; }

        Task<int> SaveChangesAsync();
        ValueTask DisposeAsync();
    }
}
