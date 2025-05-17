export function deepClone<T>(value: T): T {
    // Track visited objects to handle circular references
    const seen = new WeakMap<object, any>();
  
    function clone<U>(item: U): U {
      // Handle primitives and null
      if (item === null || typeof item !== 'object') {
        return item;
      }
  
      // Handle circular references
      if (seen.has(item as object)) {
        return seen.get(item as object);
      }
  
      // Handle Date objects
      if (item instanceof Date) {
        return new Date(item.getTime()) as unknown as U;
      }
  
      // Handle RegExp objects
      if (item instanceof RegExp) {
        return new RegExp(item.source, item.flags) as unknown as U;
      }
  
      // Handle Arrays
      if (Array.isArray(item)) {
        const copy: any[] = [];
        seen.set(item as object, copy);
        copy.push(...item.map(clone));
        return copy as unknown as U;
      }
  
      // Handle Maps
      if (item instanceof Map) {
        const copy = new Map();
        seen.set(item as object, copy);
        item.forEach((value, key) => {
          copy.set(clone(key), clone(value));
        });
        return copy as unknown as U;
      }
  
      // Handle Sets
      if (item instanceof Set) {
        const copy = new Set();
        seen.set(item as object, copy);
        item.forEach(value => {
          copy.add(clone(value));
        });
        return copy as unknown as U;
      }
  
      // Handle plain objects
      const copy: Record<string, any> = Object.create(Object.getPrototypeOf(item));
      seen.set(item as object, copy);
  
      Object.entries(item as object).forEach(([key, value]) => {
        copy[key] = clone(value);
      });
  
      return copy as U;
    }
  
    return clone(value);
  }