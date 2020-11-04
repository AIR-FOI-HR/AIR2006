using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DrinkUp.WebAPI.REST
{
    public class ObjektREST
    {
        public int Id { get; set; }
        public string Naziv { get; set; }
        public string Grad { get; set; }
        public string Ulica { get; set; }
        public string Adresa { get; set; }
        public string RadnoVrijeme { get; set; }
        public string Kontakt { get; set; }
        public double Longituda { get; set; }
        public double Latituda { get; set; }

        public ICollection<ObjektPonudaREST> ObjektPonuda { get; set; }
        public ICollection<ZaposlenikObjektREST> ZaposlenikObjekt { get; set; }
    }
}
