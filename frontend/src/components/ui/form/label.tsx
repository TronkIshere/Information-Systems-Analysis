'use client';

import { InputLabel } from '@mui/material';
import * as React from 'react';


const Label = React.forwardRef<
  React.ElementRef<typeof InputLabel>,
  React.ComponentPropsWithoutRef<typeof InputLabel>
>(({ className, ...props }, ref) => (
  <InputLabel
    ref={ref}
    className={`paragraph ${className}`}
    {...props}
  />
));
Label.displayName = 'Label';

export { Label };