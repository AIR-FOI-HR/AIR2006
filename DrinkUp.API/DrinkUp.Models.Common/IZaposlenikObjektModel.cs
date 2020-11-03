using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models.Common
{
    public interface IZaposlenikObjektModel
    {
        int Id { get; set; }
        int ObjektId { get; set; }
        int KorisnikId { get; set; }

        IKorisnikModel Korisnik { get; set; }
        IObjektModel Objekt { get; set; }
    }
}
