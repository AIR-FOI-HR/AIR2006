using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class ObjektPonuda
    {
        public int Id { get; set; }
        public int ObjektId { get; set; }
        public int PonudaId { get; set; }

        public virtual Objekt Objekt { get; set; }
        public virtual Ponuda Ponuda { get; set; }
    }
}
