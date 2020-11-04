using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class UlogaEntity
    {
        public UlogaEntity()
        {
            Korisnik = new HashSet<KorisnikTokenEntity>();
        }

        public int Id { get; set; }
        public string Naziv { get; set; }

        public virtual ICollection<KorisnikTokenEntity> Korisnik { get; set; }
    }
}
