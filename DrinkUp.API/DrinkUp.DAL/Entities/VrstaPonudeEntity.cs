using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class VrstaPonudeEntity
    {
        public VrstaPonudeEntity()
        {
            Ponuda = new HashSet<PonudaEntity>();
        }

        public int Id { get; set; }
        public string Naziv { get; set; }

        public virtual ICollection<PonudaEntity> Ponuda { get; set; }
    }
}
