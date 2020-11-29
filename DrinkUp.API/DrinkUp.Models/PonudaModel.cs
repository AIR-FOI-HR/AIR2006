using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models
{
    public class PonudaModel : IPonudaModel
    {
        public int Id { get; set; }
        public string Naslov { get; set; }
        public string Opis { get; set; }
        public double? Cijena { get; set; }
        public double? Popust { get; set; }
        public int BrojTokena { get; set; }
        public int VrstaPonudeId { get; set; }
        public int ObjektId { get; set; }

        public IObjektModel Objekt { get; set; }
        public IVrstaPonudeModel VrstaPonude { get; set; }
        public ICollection<ITokenModel> Token { get; set; }
    }
}
