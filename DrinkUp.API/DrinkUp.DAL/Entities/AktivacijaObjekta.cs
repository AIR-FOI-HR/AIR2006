using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class AktivacijaObjekta
    {
        public int Id { get; set; }
        public int ObjektId { get; set; }
        public string KodId { get; set; }

        public virtual Kod Kod { get; set; }
        public virtual Objekt Objekt { get; set; }
    }
}
