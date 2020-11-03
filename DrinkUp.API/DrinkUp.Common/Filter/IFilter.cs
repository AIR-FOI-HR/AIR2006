using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Common.Filter
{
    public interface IFilter
    {
        IQueryable<T> FilteredData<T>(IQueryable<T> data, IEnumerable<FilterParams> filterParams);
    }
}
