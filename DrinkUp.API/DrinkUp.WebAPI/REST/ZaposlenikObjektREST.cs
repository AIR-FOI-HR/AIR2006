using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DrinkUp.WebAPI.REST
{
    public class ZaposlenikObjektREST
    {
        public int Id { get; set; }
        public int ObjektId { get; set; }
        public int KorisnikId { get; set; }

        public KorisnikREST Korisnik { get; set; }
        public ObjektREST Objekt { get; set; }
    }
}
