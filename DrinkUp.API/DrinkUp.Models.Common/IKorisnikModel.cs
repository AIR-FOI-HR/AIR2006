using DrinkUp.DAL.Entities;
using System;
using System.Collections.Generic;
using System.Text;
using DrinkUp.Models;

namespace DrinkUp.Models.Common
{
    public interface IKorisnikModel
    {
        int Id { get; set; }
        string OIB { get; set; }
        string Ime { get; set; }
        string Prezime { get; set; }
        string Email { get; set; }
        Spol Spol { get; set; }
        int UlogaId { get; set; }
        bool Aktivan { get; set; }
        public string Lozinka { get; set; }

        IUlogaModel Uloga { get; set; }
        ICollection<IKorisnikAktivacija> KorisnikAktivacija { get; set; }
        ICollection<IKorisnikReset> KorisnikReset { get; set; }
        ICollection<IKorisnikTokenModel> KorisnikToken { get; set; }
        ICollection<IZaposlenikObjektModel> ZaposlenikObjekt { get; set; }
    }
}
