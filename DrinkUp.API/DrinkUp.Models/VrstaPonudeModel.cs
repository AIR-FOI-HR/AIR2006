using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models
{
    public class VrstaPonudeModel : IVrstaPonudeModel
    {
        public int Id { get; set; }
        public string Naziv { get; set; }
        
        public ICollection<IPonudaModel> Ponuda { get; set; }
    }
}
