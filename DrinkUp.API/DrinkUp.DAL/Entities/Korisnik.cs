using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class Korisnik
    {
        public Korisnik()
        {
            KorisnikAktivacija = new HashSet<KorisnikAktivacija>();
            KorisnikReset = new HashSet<KorisnikReset>();
            Token = new HashSet<Token>();
            ZaposlenikObjekt = new HashSet<ZaposlenikObjekt>();
        }

        public int Id { get; set; }
        public string Oib { get; set; }
        public string Ime { get; set; }
        public string Prezime { get; set; }
        public string Email { get; set; }
        public int Spol { get; set; }
        public int UlogaId { get; set; }
        public byte Aktivan { get; set; }
        public string Lozinka { get; set; }

        public virtual Uloga Uloga { get; set; }
        public virtual ICollection<KorisnikAktivacija> KorisnikAktivacija { get; set; }
        public virtual ICollection<KorisnikReset> KorisnikReset { get; set; }
        public virtual ICollection<Token> Token { get; set; }
        public virtual ICollection<ZaposlenikObjekt> ZaposlenikObjekt { get; set; }
    }
}
