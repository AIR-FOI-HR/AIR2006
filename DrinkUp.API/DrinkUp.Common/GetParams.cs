using DrinkUp.Common.Filter;
using DrinkUp.Common.Sort;
using DrinkUp.Common.Page;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Common
{
    public class GetParams<T> where T : class
    {
        public IEnumerable<SortingParams> SortingParam { set; get; }
        public IEnumerable<FilterParams> FilterParam { get; set; }
        public IEnumerable<string> SelectParam { get; set; }
        public string Include { get; set; }
        public int PageNumber { get; set; } = 1;
        public int PageSize { get; set; } = 10;

        public IFilter Filter { get; set; }
        public ISort Sort { get; set; }
        public IPagedResult Page { get; set; }
    }
}
