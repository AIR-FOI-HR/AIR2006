using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models
{
    public class AktivacijaObjektaModel : IAktivacijaObjektaModel
    {
        public int Id { get; set; }
        public int ObjektId { get; set; }
        public string KodId { get; set; }
        
        public IKodModel Kod { get; set; }
        public IObjektModel Objekt { get; set; }
    }
}
