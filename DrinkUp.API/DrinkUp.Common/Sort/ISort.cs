using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Common.Sort
{
    public interface ISort
    {
        IQueryable<T> SortData<T>(IQueryable<T> data, IEnumerable<SortingParams> sortingParams);
    }
}
