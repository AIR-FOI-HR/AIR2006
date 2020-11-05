using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class KorisnikAktivacija
    {
        public int Id { get; set; }
        public int KorisnikId { get; set; }
        public string KodId { get; set; }

        public Kod Kod { get; set; }
        public Korisnik Korisnik { get; set; }
    }
}
