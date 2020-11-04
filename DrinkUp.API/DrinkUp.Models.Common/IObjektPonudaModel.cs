using DrinkUp.DAL.Entities;
using System;
using System.Collections.Generic;
using System.Text;

namespace DrinkUp.Models.Common
{
    public interface IObjektPonudaModel
    {
        int Id { get; set; }
        int ObjektId { get; set; }
        int PonudaId { get; set; }

        IObjektModel Objekt { get; set; }
        IPonudaModel Ponuda { get; set; }
    }
}
