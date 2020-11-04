using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class VrstaPonude
    {
        public VrstaPonude()
        {
            Ponuda = new HashSet<Ponuda>();
        }

        public int Id { get; set; }
        public string Naziv { get; set; }

        public virtual ICollection<Ponuda> Ponuda { get; set; }
    }
}
