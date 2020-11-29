using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class KorisnikAktivacija
    {
        public int Id { get; set; }
        public int KorisnikId { get; set; }
        public string KodId { get; set; }

        public virtual Kod Kod { get; set; }
        public virtual Korisnik Korisnik { get; set; }
    }
}
