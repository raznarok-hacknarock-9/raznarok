import { api } from '@/lib/api';
import type { User } from '../../../shared/prisma/client';

export const fetchUsers = async (): Promise<User[]> => {
  const res = await api('users');
  if (!res.ok) {
    console.error('Error occurred while fetching users:', await res.text());
    return [];
  }
  return res.json();
};
