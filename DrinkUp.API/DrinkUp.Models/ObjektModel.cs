using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models
{
    public class ObjektModel : IObjektModel
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
        public bool Aktivan { get; set; }

        public ICollection<IAktivacijaObjektaModel> AktivacijaObjekta { get; set; }
        public ICollection<IObjektPonudaModel> ObjektPonuda { get; set; }
        public ICollection<IZaposlenikObjektModel> ZaposlenikObjekt { get; set; }
    }
}
