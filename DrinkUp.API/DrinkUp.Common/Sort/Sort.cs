using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace DrinkUp.Common.Sort
{
    public enum SortOrders
    {
        Asc = 1,
        Desc = 2
    }

    public class SortingParams
    {
        public SortOrders SortOrder { get; set; } = SortOrders.Asc;
        public string ColumnName { get; set; }
    }

    public class Sort : ISort
    {
        public IQueryable<T> SortData<T>(IQueryable<T> data, IEnumerable<SortingParams> sortingParams)
        {
            if (sortingParams == null)
            {
                return data;
            }

            IOrderedQueryable<T> sortedData = null;

            foreach (var sortingParam in sortingParams.Where(x => !string.IsNullOrEmpty(x.ColumnName)))
            {
                PropertyInfo col = typeof(T).GetProperty(sortingParam.ColumnName, BindingFlags.IgnoreCase | BindingFlags.Instance | BindingFlags.Public);

                if (col != null)
                {
                    sortedData = sortedData == null ? sortingParam.SortOrder == SortOrders.Asc ? data.OrderBy(x => EF.Property<object>(x, col.Name))
                                                                                               : data.OrderByDescending(x => EF.Property<object>(x, col.Name))
                                                    : sortingParam.SortOrder == SortOrders.Asc ? sortedData.ThenBy(x => EF.Property<object>(x, col.Name))
                                                                                        : sortedData.ThenByDescending(x => EF.Property<object>(x, col.Name));
                }
            }
            return sortedData ?? data;
        }
    }
}
