using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class ZaposlenikObjektEntity
    {
        public int Id { get; set; }
        public int ObjektId { get; set; }
        public int? KorisnikId { get; set; }

        public virtual KorisnikEntity Korisnik { get; set; }
        public virtual ObjektEntity Objekt { get; set; }
    }
}
