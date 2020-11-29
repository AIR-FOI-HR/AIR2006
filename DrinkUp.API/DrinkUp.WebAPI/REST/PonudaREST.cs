using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DrinkUp.WebAPI.REST
{
    public class PonudaREST
    {
        public int Id { get; set; }
        public string Naslov { get; set; }
        public string Opis { get; set; }
        public double? Cijena { get; set; }
        public double? Popust { get; set; }
        public int BrojTokena { get; set; }
        public int VrstaPonudeId { get; set; }
        public int ObjektId { get; set; }
    }
}
