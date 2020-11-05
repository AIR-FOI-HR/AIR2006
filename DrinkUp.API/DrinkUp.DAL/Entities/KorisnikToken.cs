using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class KorisnikToken
    {
        public int Id { get; set; }
        public int KorisnikId { get; set; }
        public string TokenId { get; set; }

        public virtual Korisnik Korisnik { get; set; }
        public virtual Token Token { get; set; }
    }
}
