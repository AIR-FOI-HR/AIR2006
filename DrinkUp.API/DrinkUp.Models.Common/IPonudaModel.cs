using DrinkUp.DAL.Entities;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models.Common
{
    public interface IPonudaModel
    {
        int Id { get; set; }
        string Naslov { get; set; }
        string Opis { get; set; }
        double Cijena { get; set; }
        int BrojTokena { get; set; }
        int VrstaPonudeId { get; set; }

        IVrstaPonudeModel VrstaPonude { get; set; }
        ICollection<IObjektPonudaModel> ObjektPonuda { get; set; }
        ICollection<ITokenModel> Token { get; set; }
    }
}
