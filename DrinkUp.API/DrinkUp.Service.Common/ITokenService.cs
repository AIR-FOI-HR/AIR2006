using DrinkUp.Common;
using DrinkUp.Models.Common;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Service.Common
{
    public interface ITokenService
    {
        Task<IEnumerable<ITokenModel>> GetAsync(GetParams<ITokenModel> getParams);

        Task<ITokenModel> GetAsync(string id);

        Task ActivateAsync(string id, GetParams<ITokenModel> getParams);

        Task<byte[]> InsertAsync(ITokenModel entity, GetParams<ITokenModel> getParams);

        Task UpdateAsync(ITokenModel entity);

        Task DeleteAsync(string id);
    }
}
