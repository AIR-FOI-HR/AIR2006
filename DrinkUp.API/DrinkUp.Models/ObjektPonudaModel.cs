using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models
{
    public class ObjektPonudaModel : IObjektPonudaModel
    {
        public int Id { get; set; }
        public int ObjektId { get; set; }
        public int PonudaId { get; set; }
        
        public IObjektModel Objekt { get; set; }
        public IPonudaModel Ponuda { get; set; }
    }
}
