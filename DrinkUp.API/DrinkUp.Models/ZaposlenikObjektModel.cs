using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models
{
    public class ZaposlenikObjektModel : IZaposlenikObjektModel
    {
        public int Id { get; set; }
        public int ObjektId { get; set; }
        public int KorisnikId { get; set; }
        
        public IKorisnikModel Korisnik { get; set; }
        public IObjektModel Objekt { get; set; }
    }
}
