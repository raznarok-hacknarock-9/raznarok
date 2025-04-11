import { api } from '@/lib/api';
import { User } from '@shared/prisma';

export const fetchUsers = async (): Promise<User[]> => {
  const res = await api('users');
  if (!res.ok) {
    console.error('Error occurred while fetching users:', await res.text());
    return [];
  }
  return res.json();
};
