using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class KorisnikTokenEntity
    {
        public int Id { get; set; }
        public int KorisnikId { get; set; }
        public string TokenId { get; set; }

        public virtual KorisnikTokenEntity Korisnik { get; set; }
        public virtual TokenEntity Token { get; set; }
    }
}
