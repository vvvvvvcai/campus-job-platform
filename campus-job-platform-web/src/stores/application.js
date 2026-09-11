import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getApplicationList, getReceivedApplications } from '../api/application'

export const useApplicationStore = defineStore('application', () => {
  const applicationList = ref([])
  const receivedList = ref([])
  const totalCount = ref(0)
  const loading = ref(false)

  async function fetchApplicationList(params) {
    loading.value = true
    try {
      const res = await getApplicationList(params)
      if (res.code === 200) {
        applicationList.value = res.data.records || []
        totalCount.value = res.data.total || 0
      }
    } catch (error) {
      console.error('Failed to fetch applications:', error)
    } finally {
      loading.value = false
    }
  }

  async function fetchReceivedApplications(params) {
    loading.value = true
    try {
      const res = await getReceivedApplications(params)
      if (res.code === 200) {
        receivedList.value = res.data.records || []
        totalCount.value = res.data.total || 0
      }
    } catch (error) {
      console.error('Failed to fetch received applications:', error)
    } finally {
      loading.value = false
    }
  }

  return {
    applicationList,
    receivedList,
    totalCount,
    loading,
    fetchApplicationList,
    fetchReceivedApplications
  }
})
