using DrinkUp.Common;
using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service.Common
{
    public interface IObjektService
    {
        Task<IEnumerable<IObjektModel>> GetAsync(GetParams<IObjektModel> getParams);

        Task<IObjektModel> GetAsync(int id);

        Task InsertAsync(IObjektModel entity);

        Task UpdateAsync(IObjektModel entity);

        Task DeleteAsync(int id);
    }
}
