using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class UlogaEntity
    {
        public UlogaEntity()
        {
            Korisnik = new HashSet<KorisnikEntity>();
        }

        public int Id { get; set; }
        public string Naziv { get; set; }

        public virtual ICollection<KorisnikEntity> Korisnik { get; set; }
    }
}
