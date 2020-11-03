using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models.Common
{
    public interface IVrstaPonudeModel
    {
        int Id { get; set; }
        string Naziv { get; set; }

        ICollection<IPonudaModel> Ponuda { get; set; }
    }
}
