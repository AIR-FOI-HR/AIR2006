using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class ZaposlenikObjekt
    {
        public int Id { get; set; }
        public int ObjektId { get; set; }
        public int KorisnikId { get; set; }

        public virtual Korisnik Korisnik { get; set; }
        public virtual Objekt Objekt { get; set; }
    }
}
