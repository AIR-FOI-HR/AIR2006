using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class Kod
    {
        public Kod()
        {
            AktivacijaObjekta = new HashSet<AktivacijaObjekta>();
            KorisnikAktivacija = new HashSet<KorisnikAktivacija>();
            KorisnikReset = new HashSet<KorisnikReset>();
        }

        public string Id { get; set; }
        public DateTime DatumKreiranja { get; set; }

        public virtual ICollection<AktivacijaObjekta> AktivacijaObjekta { get; set; }
        public virtual ICollection<KorisnikAktivacija> KorisnikAktivacija { get; set; }
        public virtual ICollection<KorisnikReset> KorisnikReset { get; set; }
    }
}
