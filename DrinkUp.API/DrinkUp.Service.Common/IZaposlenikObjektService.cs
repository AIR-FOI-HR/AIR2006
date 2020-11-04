using DrinkUp.Common;
using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service.Common
{
    public interface IZaposlenikObjektService
    {
        Task<IEnumerable<IZaposlenikObjektModel>> GetAsync(GetParams<IZaposlenikObjektModel> getParams);

        Task<IZaposlenikObjektModel> GetAsync(int id);

        Task InsertAsync(IZaposlenikObjektModel entity);

        Task UpdateAsync(IZaposlenikObjektModel entity);

        Task DeleteAsync(int id);
    }
}
