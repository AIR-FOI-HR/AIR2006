using System;
using System.Collections.Generic;

namespace DrinkUp.DAL.Entities
{
    public partial class ObjektEntity
    {
        public ObjektEntity()
        {
            ObjektPonuda = new HashSet<ObjektPonudaEntity>();
            ZaposlenikObjekt = new HashSet<ZaposlenikObjektEntity>();
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

        public virtual ICollection<ObjektPonudaEntity> ObjektPonuda { get; set; }
        public virtual ICollection<ZaposlenikObjektEntity> ZaposlenikObjekt { get; set; }
    }
}
