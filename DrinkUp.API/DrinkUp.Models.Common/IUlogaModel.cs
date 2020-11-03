using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models.Common
{
    public interface IUlogaModel
    {
        int Id { get; set; }
        string Naziv { get; set; }

        ICollection<IKorisnikModel> Korisnik { get; set; }
    }
}
