using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public enum Spol
    {
        Musko, Zensko
    }

    public partial class KorisnikEntity
    {
        public KorisnikEntity()
        {
            KorisnikToken = new HashSet<KorisnikTokenEntity>();
            ZaposlenikObjekt = new HashSet<ZaposlenikObjektEntity>();
        }

        public int Id { get; set; }
        public string Oib { get; set; }
        public string Ime { get; set; }
        public string Prezime { get; set; }
        public string Email { get; set; }
        public int Spol { get; set; }
        public int UlogaId { get; set; }
        public byte Aktivan { get; set; }

        public virtual UlogaEntity Uloga { get; set; }
        public virtual ICollection<KorisnikTokenEntity> KorisnikToken { get; set; }
        public virtual ICollection<ZaposlenikObjektEntity> ZaposlenikObjekt { get; set; }
    }
}
