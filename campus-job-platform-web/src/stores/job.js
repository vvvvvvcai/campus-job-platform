import { defineStore } from 'pinia'
import { ref } from 'vue'
import { searchJobs, getJobDetail, publishJob } from '../api/job'

export const useJobStore = defineStore('job', () => {
  const jobList = ref([])
  const currentJob = ref(null)
  const totalCount = ref(0)
  const loading = ref(false)

  async function searchJobsAction(params) {
    loading.value = true
    try {
      const res = await searchJobs(params)
      if (res.code === 200) {
        jobList.value = res.data.records || []
        totalCount.value = res.data.total || 0
      }
    } catch (error) {
      console.error('Failed to search jobs:', error)
    } finally {
      loading.value = false
    }
  }

  async function fetchJobDetail(id) {
    try {
      const res = await getJobDetail(id)
      if (res.code === 200) {
        currentJob.value = res.data
      }
    } catch (error) {
      console.error('Failed to fetch job detail:', error)
    }
  }

  async function publishJobAction(data) {
    try {
      const res = await publishJob(data)
      if (res.code === 200) {
        return { success: true }
      }
      return { success: false, message: res.message }
    } catch (error) {
      return { success: false, message: error.message || '发布失败' }
    }
  }

  return {
    jobList,
    currentJob,
    totalCount,
    loading,
    searchJobsAction,
    fetchJobDetail,
    publishJobAction
  }
})
