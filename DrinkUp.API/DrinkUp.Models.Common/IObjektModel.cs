using DrinkUp.DAL.Entities;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models.Common
{
    public interface IObjektModel
    {
        int Id { get; set; }
        string Naziv { get; set; }
        string Grad { get; set; }
        string Ulica { get; set; }
        string Adresa { get; set; }
        string RadnoVrijeme { get; set; }
        string Kontakt { get; set; }
        double Longituda { get; set; }
        double Latituda { get; set; }
        bool Aktivan { get; set; }

        ICollection<IPonudaModel> Ponuda { get; set; }
        ICollection<IAktivacijaObjektaModel> AktivacijaObjekta { get; set; }
        ICollection<IZaposlenikObjektModel> ZaposlenikObjekt { get; set; }
    }
}
