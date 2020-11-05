using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DrinkUp.WebAPI.REST
{
    public class ObjektPonudaREST
    {
        public int Id { get; set; }
        public int ObjektId { get; set; }
        public int PonudaId { get; set; }

        public ObjektREST Objekt { get; set; }
        public PonudaREST Ponuda { get; set; }
    }
}
