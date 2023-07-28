import { Usuario } from '@/models/Usuario';
import { useLocalStorage } from './useLocalStorage';

export function useAuth() {
  return useLocalStorage<Usuario>;
}
