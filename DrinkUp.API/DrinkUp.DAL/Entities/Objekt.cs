using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class Objekt
    {
        public Objekt()
        {
            ObjektPonuda = new HashSet<ObjektPonuda>();
            ZaposlenikObjekt = new HashSet<ZaposlenikObjekt>();
        }

        public int Id { get; set; }
        public string Naziv { get; set; }
        public string Grad { get; set; }
        public string Ulica { get; set; }
        public string Adresa { get; set; }
        public string RadnoVrijeme { get; set; }
        public string Kontakt { get; set; }
        public double Longituda { get; set; }
        public double Latituda { get; set; }
        public byte Aktivan { get; set; }
        public string KodZaAktivaciju { get; set; }

        public virtual ICollection<ObjektPonuda> ObjektPonuda { get; set; }
        public virtual ICollection<ZaposlenikObjekt> ZaposlenikObjekt { get; set; }
    }
}
