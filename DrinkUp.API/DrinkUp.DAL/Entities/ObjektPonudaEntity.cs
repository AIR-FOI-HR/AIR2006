using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class ObjektPonudaEntity
    {
        public int Id { get; set; }
        public int ObjektId { get; set; }
        public int PonudaId { get; set; }

        public virtual ObjektEntity Objekt { get; set; }
        public virtual PonudaEntity Ponuda { get; set; }
    }
}
